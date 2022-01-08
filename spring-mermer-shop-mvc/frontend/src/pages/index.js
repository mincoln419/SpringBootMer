import React from 'react';
React.useLayoutEffect = React.useEffect;
import { Typography, Divider } from 'antd';
import wrapper from '../store/configureStore';
import { LOG_IN_STATE_UPDATE} from '../actions';
import { END } from 'redux-saga';


const { Title, Paragraph, Text, Link } = Typography;

const Index = () =>{
    return (
    <Typography>
      <Title>Introduction</Title>
      <Divider style={{ borderWidth: 2, borderColor: 'black' }} />
      <Paragraph>
        In the process of internal desktop applications development, many different design specs and
        implementations would be involved, which might cause designers and developers difficulties and
        duplication and reduce the efficiency of development.
      </Paragraph>
    </Typography>
      );
    };

  //리덕스에 데이터가 채워진상태로 랜더링된다.
  export const getServerSideProps = wrapper.getServerSideProps(async (context)=>{
    
    const cookies = context.req.cookies;
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

export default Index;