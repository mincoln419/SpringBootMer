import React from 'react';
React.useLayoutEffect = React.useEffect;
import { Typography, Divider } from 'antd';
import wrapper from '../store/configureStore';
import { LOG_IN_STATE_UPDATE} from '../actions';
import { END } from 'redux-saga';


const { Title, Paragraph, Text, Link } = Typography;

const About = () =>{
    return (
    <Typography>
      <Title>Introduction</Title>
      <Divider style={{ borderWidth: 2, borderColor: 'black' }} />
      <Paragraph>
<main class="px-3">
    <div class="container col-xxl-8 px-4 py-5">
        <div class="row flex-lg-row-reverse align-items-center g-5 py-5">
          <div class="col-10 col-sm-8 col-lg-6">
            <img src="/mincoln.jpg" class="d-block mx-lg-auto img-fluid" alt="Bootstrap Themes" width="700" height="500" loading="lazy"/>
          </div>
          <div class="col-lg-6">
            <h1 class="display-5 fw-bold lh-1 mb-3">안녕하세요</h1>
            <p class="lead">웹 기반 시스템 개발자<br/> 조민건입니다</p>
            
            <div class="d-grid gap-2 d-md-flex justify-content-md-start">
              <button type="button" class="btn btn-primary btn-lg px-4 me-md-2">경력기술서</button>
              <button type="button" class="btn btn-outline-secondary btn-lg px-4">자기소개</button>
            </div>
          </div>
        </div>
      </div>
</main>
      </Paragraph>
    </Typography>
      );
    };

    export const getServerSideProps = wrapper.getServerSideProps(async (context)=>{
    
      const cookies = context.req? context.req.cookies : '';
        const state =  context.store.getState();
    
        if(!state.user.isLoggedIn && cookies.loginId){
          context.store.dispatch(
            {
                type: LOG_IN_STATE_UPDATE,
                accountId: cookies.accountId,
                token: cookies.token,
                login: cookies.loginId
            }
        );
        }
     
        context.store.dispatch(END);
        await context.store.sagaTask.toPromise();    
      });


//   //리덕스에 데이터가 채워진상태로 랜더링된다.
//   export const getStaticProps = wrapper.getStaticProps(async (context)=>{
//     //한번 만들어놓으면 내용이 바뀌지 않는 경우 -> 정적인 HTML 파일로 뽑아줌
//     console.log("context---",context);
//     const cookies = context.req.cookies;
//     const state =  context.store.getState();

//     if(!state.user.isLoggedIn && cookies.loginId){
//       context.store.dispatch(
//         {
//             type: LOG_IN_STATE_UPDATE,
//             accountId: cookies.accountId,
//             token: cookies.token,
//             login: cookies.loginId
//         }
//     );
//     }
 
//     context.store.dispatch(END);
//     await context.store.sagaTask.toPromise();    
//   });

export default About;