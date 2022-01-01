import React, { Component} from 'react';
React.useLayoutEffect = React.useEffect;
import { Table, Tag, Space, Divider } from 'antd';
import axios from 'axios';
import { useSelector } from 'react-redux';
import NoticeForm from '../../components/NoticeForm';
import PostCard from '../../components/PostCard';


const NoticeDetail = () => {
    
    const {isLoggedIn} = useSelector((state) => state.user);
    const { mainPosts } = useSelector((state) => state.notice);

    return (
  <>
    {isLoggedIn && (
        <>
            <NoticeForm/>
            {mainPosts.map((notice, index) => <PostCard key= {index} notice = {notice}/>)} 
        </>
    )
    
    
    }
    
  </>
);
}

export default NoticeDetail;