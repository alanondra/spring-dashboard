const path = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const RemovePlugin = require('remove-files-webpack-plugin');

const envName = process.env.NODE_ENV || 'development';
const isProduction = envName == 'production';

const resourcePath = './src/main/resources';
const staticPath = path.resolve(resourcePath, 'static');
const buildPath = path.resolve(staticPath, 'assets/build');
const publicPrefix = '/assets/build';

const resource = (res) => path.resolve(resourcePath, res);

const entries = {
	styles: {
		'app': resource('sass/app.scss')
	},
	scripts: {
		'app': resource('ts/app.ts')
	}
};

module.exports = [
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
				filename: 'css/[name].css'
			}),

			new RemovePlugin({
				after: {
					test: [
						{
							folder: buildPath,
							recursive: true,

							method: (path) => {
								return new RegExp(/\.css\.js$/, 'm').test(path);
							}
						}
					]
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
								sourceMap: false
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
								sourceMap: false
							}
						},
						{
							loader: 'resolve-url-loader',
							options: {
								keepQuery: true
							}
						},
						{
							loader: 'sass-loader',
							options: {
								sourceMap: true,
								additionalData: `$env: ${envName};`
							}
						}
					],
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

		plugins: [
			//
		],

		output: {
			path: buildPath,

			publicPath: publicPrefix,

			filename: 'js/[name].js'
		},

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
			extensions: [
				'.tsx',
				'.ts',
				'.js'
			]
		}
	}
];
