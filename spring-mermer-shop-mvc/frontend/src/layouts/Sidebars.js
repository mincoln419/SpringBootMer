import React, {useEffect, useState} from 'react';
React.useEffectLayout = React.useEffect;
import {Layout, Menu } from 'antd';
import {UserOutlined, FileDoneOutlined, TeamOutlined} from '@ant-design/icons';
import Link from 'next/link';
const {SubMenu} = Menu;
const {Header, Content, Sider, Footer} = Layout;

const Sidebars = (props) => {
    
     return (
            <Sider style={{
                    overflow: 'auto',
                    height: '100vh',
                    position: 'fixed',
                    left: 0,
                }}>
                    <div className="logo" />
                    <Menu
                        theme="dark"
                        mode="inline"
                        defaultSelectedKeys={['1']}
                        defaultOpenKeys={['sub1']}
                    >
                        <SubMenu
                            key="sub1"
                            title={<span><UserOutlined /><span>User</span></span>}
                        >
                            <Menu.Item key="3"><Link href="/notice"><a>Notice</a></Link></Menu.Item>
                            <Menu.Item key="4">Bill</Menu.Item>
                            <Menu.Item key="5">Alex</Menu.Item>
                        </SubMenu>
                        <SubMenu
                            key="sub2"
                            title={<span><TeamOutlined /><span>Team</span></span>}
                        >
                            <Menu.Item key="6">Team 1</Menu.Item>
                            <Menu.Item key="8">Team 2</Menu.Item>
                        </SubMenu>
                        <Menu.Item key="9">
                            <FileDoneOutlined/>
                            <span>File</span>
                        </Menu.Item>
                    </Menu>
                </Sider>
                 )
}
export default Sidebars;