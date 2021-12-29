import React, {useEffect, useState} from 'react';
import {Layout, Menu } from 'antd';
import {UserOutlined, FileDoneOutlined, TeamOutlined} from '@ant-design/icons';
import {BrowserRouter, Routes, Route, Link} from "react-router-dom";

const {SubMenu} = Menu;
const {Header, Content, Sider, Footer} = Layout;

const Sidebar = (props) => {
    
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
                            <Link to="/notice"><Menu.Item key="3">Notice</Menu.Item></Link>
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
export default Sidebar;