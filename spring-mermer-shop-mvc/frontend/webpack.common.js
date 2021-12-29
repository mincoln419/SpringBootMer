const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const isProd = process.env.NODE_ENV === 'production'

const config = {
    entry: {
        index: path.resolve(__dirname, 'src/index.js')
    },
    output: {
        path: path.resolve(__dirname, 'public'),
        publicPath: '/',
        filename: '[name]-[hash].js',
        chunkFilename: 'chunk-[chunkhash].js',
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: [path.resolve(__dirname, 'node_modules')],
                use: {
                    loader: "babel-loader"
                }
            },
            {
                test: /\.css$/,
                use: ["style-loader", "css-loader"],
            },
            {
                test: /\.(png|svg|jpe?g|gif)$/,
                loader:'file-loader'
            }
        ]
    },
    plugins: [
        new CleanWebpackPlugin({cleanAfterEveryBuildPatterns: ['public']}),
        new HtmlWebpackPlugin({
            minify: isProd
                ? true
                : {
                    collapseWhitespace: true,
                    removeComments: true,
                    useShortDoctype: true,
                    minifyCSS: true,
                },
            template: './static/index.html',
            title: '형량 계산기',
            description: `형량 계산기 웹페이지`,
            url: 'http://mermer-api.kr',
        })
    ]
};

module.exports = config