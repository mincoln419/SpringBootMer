import React from 'react';

import { List, Typography, Divider } from 'antd';
import axios from 'axios';
import { Component, useState } from 'react/cjs/react.development';

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
    <List
      bordered
      dataSource={this.state.data}
      renderItem={item => (
        <List.Item key={item.id}>
          <Typography.Text mark>[ITEM]</Typography.Text> {item.title} {item.insterId}
        </List.Item>
      )}
    />
  </>
);
}
}
