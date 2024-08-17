#!/usr/bin/env fish

cp -r .devcontainer/home/. /home/vscode/

# https://github.com/jorgebucaran/fisher
curl -sL https://raw.githubusercontent.com/jorgebucaran/fisher/main/functions/fisher.fish | source && fisher install jorgebucaran/fisher
