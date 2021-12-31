import React from 'react';

import { Table, Tag, Space, Divider } from 'antd';
import axios from 'axios';
import { Component, useState } from 'react/cjs/react.development';

const columns = [
  {
    title: 'id',
    dataIndex: 'id',
    key: 'id',
    render: text => <a>{text}</a>,
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

export default class Notice extends Component{
    
    constructor(){
        super();
        this.state = {data : []};
    }
    
    componentDidMount(){
        axios.get("/api/notice")
        .then((response) => {
            const data = Object.values(response.data._embedded.tupleBackedMapList);
            this.setState({data});
            //setData(response.data._embedded.tupleBackedMapList);
        });
    }

    

    render(){
        return (
  <>
    <Divider orientation="left">Default Size</Divider>
    <Table columns={columns} dataSource={this.state.data} />
  </>
);
}
}
