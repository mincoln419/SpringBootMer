import React, {useEffect, useState} from 'react';
React.useEffectLayout = React.useEffect;
import axios from 'axios';
import {Layout, Menu } from 'antd';
import Link from 'next/link';
const {SubMenu} = Menu;
const {Header, Content, Sider, Footer} = Layout;


const Headers = (props) => {
    
     return (
         <>
        <Header className="site-layout-background" style={{ padding: 0 , alignmentBaseline:'right'}} >
        <Menu mode="horizontal" defaultSelectedKeys={['2']}>
        <Menu.Item key="1"><Link href="/"><a>Home</a></Link></Menu.Item>
        <Menu.Item key="2"><Link href="/login"><a>Login</a></Link></Menu.Item>
        <Menu.Item key="3"><Link href="/sign-up"><a>Sign Up</a></Link></Menu.Item>
        {useState.accessToken && <Menu.Item key="4"><Link href="/Logout"><a>Logout</a></Link></Menu.Item>}
        </Menu>
        </Header>
        </>
                 );
}

export default Headers;