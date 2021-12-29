const React = require('react');
const {useEffect, useState} = require('react');
const axios = require('axios');
import {Layout, Menu } from 'antd';
import {UserOutlined, FileDoneOutlined, TeamOutlined} from '@ant-design/icons';
import './App.css';
import Sidebar from './layouts/Sidebar.js';
import Headers from './layouts/Headers.js';
import Notice from './components/Notice';
import Login from './components/Login';
import SignUp from './components/SignUp';
import {BrowserRouter, Routes, Route, Link} from "react-router-dom";

const {SubMenu} = Menu;
const {Header, Content, Sider, Footer} = Layout;

function App() {
    const [message, setMessage] = useState('오늘');

    useEffect(() => {
        axios.get('/api/notice')
            .then((response) => setMessage(response.data._embedded.tupleBackedMapList[0].title))
    })

    return (
        <BrowserRouter>
        <Layout style={{ minHeight: '100vh' }}>
            <Sidebar />
            <Layout className="site-layout" style={{ marginLeft: 200 }}>
                <Headers />
                <Content
                    className="site-layout-background"
                    style={{
                            padding: 24,
                            margin: '24px 64px',
                            minHeight: 280,
                            
                            verticalAlign:"left"
                        }}>
                     <Routes>
                        <Route path = "/notice" element={<Notice />}/>
                        <Route path = "/login" element={<Login />}/>
                        <Route path = "/sign-up" element={<SignUp/>}/>
                     </Routes>
                </Content>
                <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
            </Layout>
        </Layout>
        </BrowserRouter>
    );
}

export default App;