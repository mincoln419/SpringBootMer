import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {Layout, Menu } from 'antd';
import {UserOutlined, FileDoneOutlined, TeamOutlined} from '@ant-design/icons';
import './App.css';
import Sidebar from './components/Sidebar.js';
import Headers from './components/Headers.js';

const {SubMenu} = Menu;
const {Header, Content, Sider, Footer} = Layout;

function App() {
    const [message, setMessage] = useState('오늘');

    useEffect(() => {
        axios.get('/api/notice')
            .then((response) => setMessage(response.data._links.self.href))
    })

    return (
        <Layout style={{ minHeight: '100vh' }}>
            <Sidebar />
            <Layout className="site-layout" style={{ marginLeft: 200 }}>
                <Headers />
                <Content style={{ margin: '24px 16px 0', overflow: 'initial' }}>
                    <div className="site-layout-background" style={{ padding: 24, textAlign: 'center' }}>
                        <br />
                        {message}
                        <br />
                    </div>
                </Content>
                <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
            </Layout>
        </Layout>
    );
}

export default App;