import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Layout, Menu } from 'antd';
import '../App.css';
import {BrowserRouter, Routes, Route, Link} from "react-router-dom";
const {SubMenu} = Menu;
const {Header, Content, Sider, Footer} = Layout;

const Headers = (props) => {
    
     return (
        <Header className="site-layout-background" style={{ padding: 0 , alignmentBaseline:'right'}} >
        <Menu mode="horizontal" defaultSelectedKeys={['2']}>
        <Link to="/login"><Menu.Item key="1">Login</Menu.Item></Link>
        <Link to="/sign-up"><Menu.Item key="2">Sign Up</Menu.Item></Link>
        {props.isLogin && <Menu.Item key="3">Logout</Menu.Item>}
        </Menu>
    </Header>
                 );
}

export default Headers;