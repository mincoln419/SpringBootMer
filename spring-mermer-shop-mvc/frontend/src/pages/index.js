import React from 'react';
React.useLayoutEffect = React.useEffect;
import { Typography, Divider } from 'antd';
import wrapper from '../store/configureStore';
import { LOAD_USER_REQUEST } from '../actions';
import {END} from "redux-saga";

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

// export const getServerSideProps = wrapper.getServerSideProps((context)=>{

//   context.store.dispatch({
//     type: LOAD_USER_REQUEST
//   });

//   context.store.dispatch(END);
//   await context.store.sagaTask.toPromise();
// });

export default Index;