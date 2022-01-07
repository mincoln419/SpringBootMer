import React, { Component, useEffect, useState} from 'react';
React.useLayoutEffect = React.useEffect;
import { Table, Tag, Space, Divider } from 'antd';
import Link from 'next/link';
import { useDispatch, useSelector } from 'react-redux';
import { queryNoticeRequestAction } from '../../reducer/notice';
import styled from 'styled-components';
import wrapper from '../../store/configureStore';
import { END } from 'redux-saga';
import { LOG_IN_STATE_UPDATE } from '../../actions';

const columns = [
  {
    title: 'id',
    dataIndex: 'id',
    key: 'id',
    width: '10%',
    render: text => <Link href={"/notices/detail?" + text}><a>{text}</a></Link>,
  },
  {
    title: 'title',
    dataIndex: 'title',
    key: 'title',
    width: '50%',
    render: (text, record) =>  <Link href={"/notices/detail?" + record.id}><a>{text}</a></Link>,
    ellipsis: true,
  },
  {
    title: '등록시간',
    dataIndex: 'instDtm',
    key: 'instDtm',
    ellipsis: true,
  }
];


export const Overlay = styled.div`
  padding:20px;
`;
const Notice = () =>{

  const dispatch = useDispatch();
  
  //화면 전환시 한번만 호출
  useEffect(() => {
    dispatch(queryNoticeRequestAction());
  }, []);
  
  const {noticeList} = useSelector((state) => state.notice);

        return (
  <>
    
    <Divider orientation="left">공지사항</Divider>
    <Table columns={columns} dataSource={noticeList} key={noticeList} rowKey={item => item.id}/>
    <Overlay></Overlay>
  </>
);
}

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


export default Notice;