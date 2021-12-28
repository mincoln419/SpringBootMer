import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Layout, Menu } from 'antd';
import {UserOutlined, FileDoneOutlined, TeamOutlined} from '@ant-design/icons';
import '../App.css';
const {SubMenu} = Menu;
const {Header, Content, Sider, Footer} = Layout;

const Headers = (props) => {
    
     return (
        <Header className="site-layout-background" style={{ padding: 0 }} >
        <Menu mode="horizontal" defaultSelectedKeys={['2']}>
            <Menu.Item key="1">nav 1</Menu.Item>
            <Menu.Item key="2">nav 2</Menu.Item>
            <Menu.Item key="3">nav 3</Menu.Item>
        </Menu>
    </Header>
                 );
}

export default Headers;