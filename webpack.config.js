const path = require('path');
const fs = require('fs');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const RemovePlugin = require('remove-files-webpack-plugin');
const { WebpackManifestPlugin } = require('webpack-manifest-plugin');

const resourcePath = './src/main/resources';
const staticPath = path.resolve(resourcePath, 'static');
const buildPath = path.resolve(staticPath, 'assets/build');
const publicPrefix = '/assets/build';
const manifestPath = path.resolve(staticPath, 'manifest.json');

const resource = (res) => path.resolve(resourcePath, res);

const entries = {
	styles: {
		'app': resource('sass/app.scss')
	},
	scripts: {
		'app': resource('ts/app.ts')
	}
};

let sharedManifest = {};

const clearManifest = () => {
	if (fs.existsSync(manifestPath)) {
		fs.unlinkSync(manifestPath);
	}
};

module.exports = (env, argv) => {
	clearManifest();

	const mode = argv.mode || 'development';
	const isProduction = mode === 'production';

	return [
		{
			entry: entries.styles,

			output: {
				path: buildPath,
				publicPath: publicPrefix,
				// https://webpack.js.org/configuration/output/#outputfilename
				filename: 'css/[name].css.js'
			},

			plugins: [
				new MiniCssExtractPlugin({
					// https://webpack.js.org/configuration/output/#outputfilename
					filename: isProduction
						? 'css/[name].[contenthash:8].css'
						: 'css/[name].css'
				}),

				new RemovePlugin({
					after: {
						test: [
							{
								folder: buildPath,
								recursive: true,
								method: (filePath) => /\.css\.js$/.test(filePath)
							}
						]
					}
				}),

				new WebpackManifestPlugin({
					fileName: path.relative(buildPath, manifestPath),
					publicPath: publicPrefix,
					seed: sharedManifest,
					filter: (file) => /\.css$/.test(file.name),
					generate: (seed, files) => {
						const manifest = files.reduce((manifest, file) => {
							const directory = file.path.substring(0, file.path.lastIndexOf('/'));
							const name = `${directory}/${file.name}`;
							manifest[name] = file.path;
							return manifest;
						}, seed);
						return manifest;
					}
				})
			],

			module: {
				rules: [
					{
						test: /\.css$/i,
						use: [
							MiniCssExtractPlugin.loader,
							{
								loader: 'css-loader',
								options: {
									sourceMap: true
								}
							}
						]
					},
					{
						test: /\.s(a|c)ss$/,
						use: [
							MiniCssExtractPlugin.loader,
							{
								loader: 'css-loader',
								options: {
									sourceMap: true
								}
							},
							{
								loader: 'resolve-url-loader',
								options: {
									keepQuery: true,
									sourceMap: true,
									root: staticPath
								}
							},
							{
								loader: 'sass-loader',
								options: {
									api: 'legacy',
									implementation: require('sass'),
									sourceMap: true,
									additionalData: `$env: ${mode};`,
									sassOptions: {
										style: 'compressed',
										quietDeps: true,
										sourceMap: true
									}
								}
							}
						]
					},
					{
						test: /\.(gif|jpe?g|png|svg)$/i,
						type: 'asset/resource',
						generator: {
							filename: 'img/[name][ext][query]'
						}
					},
					{
						test: /\.(eot|otf|ttf|woff2?)$/i,
						type: 'asset/resource',
						generator: {
							filename: 'fonts/[name][ext][query]'
						}
					}
				]
			}
		},
		{
			entry: entries.scripts,

			devtool: isProduction
				? 'source-map'
				: 'inline-source-map',

			output: {
				path: buildPath,
				publicPath: publicPrefix,
				// https://webpack.js.org/configuration/output/#outputfilename
				filename: isProduction
					? 'js/[name].[contenthash:8].js'
					: 'js/[name].js'
			},

			plugins: [
				new WebpackManifestPlugin({
					fileName: path.relative(buildPath, manifestPath),
					publicPath: publicPrefix,
					seed: sharedManifest,
					filter: (file) => /\.js$/.test(file.name),
					generate: (seed, files) => {
						const manifest = files.reduce((manifest, file) => {
							const directory = file.path.substring(0, file.path.lastIndexOf('/'));
							const name = `${directory}/${file.name}`;
							manifest[name] = file.path;
							return manifest;
						}, seed);
						return manifest;
					}
				})
			],

			module: {
				rules: [
					{
						test: /\.tsx?$/,
						use: 'ts-loader',
						exclude: /node_modules/
					}
				]
			},

			resolve: {
				extensions: ['.tsx', '.ts', '.js']
			}
		}
	];
};
