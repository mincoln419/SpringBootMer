import React, { Component} from 'react';
React.useLayoutEffect = React.useEffect;
import { Table, Tag, Space, Divider } from 'antd';
import axios from 'axios';
import { useSelector } from 'react-redux';
import NoticeForm from '../../components/NoticeForm';
import PostCard from '../../components/PostCard';
import { END } from 'redux-saga';
import { LOG_IN_STATE_UPDATE } from '../../actions';
import wrapper from '../../store/configureStore';


const NoticeDetail = () => {
    
    const {isLoggedIn} = useSelector((state) => state.user);
    const { mainPosts } = useSelector((state) => state.notice);

    return (
  <>
    {isLoggedIn && (
        <>
            <NoticeForm />
            {mainPosts.map((notice, index) => <PostCard key= {index} notice = {notice}/>)}
            <div style={{position: 'relative', margin:100}}></div>
        </>
    )
    
    
    }
    
  </>
);
}
  //리덕스에 데이터가 채워진상태로 랜더링된다.
  export const getServerSideProps = wrapper.getServerSideProps(async (context)=>{
    
    const cookies = context.req.cookies
    
    if(cookies.loginId){
      context.store.dispatch(
        {
            type: LOG_IN_STATE_UPDATE,
            token: cookies.token,
            login: cookies.loginId
        }
    );
    }
 
    context.store.dispatch(END);
    await context.store.sagaTask.toPromise();    
  });

export default NoticeDetail;