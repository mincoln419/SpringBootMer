import React, { Component} from 'react';
React.useLayoutEffect = React.useEffect;
import { Table, Tag, Space, Divider } from 'antd';
import axios from 'axios';
import Link from 'next/link';

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
    <Divider orientation="left">공지사항</Divider>
    <Table columns={columns} dataSource={this.state.data} key={this.state.data.id}/>
  </>
);
}
}
