const { merge } = require('webpack-merge')
const common = require('./webpack.common.js')

const config = {
    mode: 'production',
    devtool: 'source-map',
    optimization: {
        runtimeChunk: {
            name: "runtime"
        },
        splitChunks: {
            name: "vendor",
            chunks: "all"
        },
    }
}

module.exports = merge(common, config)