import React, { Component, useEffect, useState} from 'react';
React.useLayoutEffect = React.useEffect;
import { Table, Tag, Space, Divider } from 'antd';
import Link from 'next/link';
import { useDispatch, useSelector } from 'react-redux';
import { queryNoticeRequestAction } from '../../reducer/notice';

const columns = [
  {
    title: 'id',
    dataIndex: 'id',
    key: 'id',
    render: text => <Link href={"/notices/detail?" + text}><a>{text}</a></Link>,
  },
  {
    title: 'title',
    dataIndex: 'title',
    key: 'title',
  },
  {
    title: '등록시간',
    dataIndex: 'instDtm',
    key: 'instDtm',
  }
];

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
    <Table columns={columns} dataSource={noticeList} key={noticeList}/>
  </>
);
}

export default Notice;