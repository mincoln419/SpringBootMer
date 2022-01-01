// next.config.js
const path = require('path');
const { config } = require('dotenv');
// dotenv 변수를 아래 커스텀 설정에서 사용하려고 미리 env 변수를 적용
config({
  path: path.resolve(
    process.cwd(),
    process.env.NODE_ENV === 'development' ? '.env.local' : '.env',
  )
});
const prod = process.env.NODE_ENV === 'production';

module.exports = {
  // 전역적으로 사용할 env 변수
  env: {
    PUBLIC_URL: process.env.PUBLIC_URL,
    PUBLIC_IMAGE_URL: process.env.PUBLIC_IMAGE_URL,
    DEVLOG_SERVICE_URL: process.env.DEVLOG_SERVICE_URL
  },
  // server, serverless 등 어느 환경을 타겟으로 빌드할지
  target: process.env.BUILD_TARGET,
  // 정적파일을 제공할 경로, 내 경우엔 cloudfront CDN 주소
  assetPrefix: prod ? process.env.PUBLIC_URL : '',
  compress: true,

  async rewrites() {
      return [

        {
          destination: process.env.TOKEN_URL,
          source: process.env.TOKEN_PATH,
        },
        {
          destination: process.env.DESTINATION_URL,
          source: process.env.SOURCE_PATH,
        },

      ]
  }
};