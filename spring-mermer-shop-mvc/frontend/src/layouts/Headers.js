import React, {useEffect, useState} from 'react';
React.useEffectLayout = React.useEffect;
import axios from 'axios';
import {Layout, Menu } from 'antd';
import Link from 'next/link';
import Profile from './Profiles';
import styled from 'styled-components';
const {SubMenu} = Menu;
const {Header, Content, Sider, Footer} = Layout;

const MenuWrapper = styled.div`
       
    color: white;
    padding: 0;
`;


const Headers = (props) => {
    
     return (
         <>
         <MenuWrapper>
        <Header className="site-layout-background" style={{float : "right", padding: 0, paddingRight : 20 }} >
        
        <Menu mode="horizontal" >
        <Menu.Item key="2"><Link href="/login"><a>Login</a></Link></Menu.Item>
        <Menu.Item key="3"><Link href="/sign-up"><a>Sign Up</a></Link></Menu.Item>
        {useState.accessToken && <Menu.Item key="4"><Link href="/Logout"><a>Logout</a></Link></Menu.Item>}
        <Menu.Item key="5"><Profile/></Menu.Item>
        </Menu>
        <Menu mode="horizontal" defaultSelectedKeys={['1']} >
        <Menu.Item key="1"><Link href="/"><a>Home</a></Link></Menu.Item>
        </Menu>
        </Header>
        </MenuWrapper>
        
        
        </>
                 );
}

export default Headers;