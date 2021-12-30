import React from 'react';
React.useEffectLayout = React.useEffect;
import axios from 'axios';
import 'antd/dist/antd.css';
import Headers from "../layouts/Headers";
import Sidebars from "../layouts/Sidebars";
import {Layout, Menu, Row, Col} from 'antd';
const {SubMenu} = Menu;
const {Header, Content, Sider, Footer} = Layout;


const App = ({ Component }) => {
    return (
        <>
            <Layout style={{ minHeight: '100vh' }}>
            <Sidebars/>
                <Layout className="site-layout" style={{ marginLeft: 200 }}>
                <header><Headers/></header>
                <Content
                    className="site-layout-background"
                    style={{
                            padding: 24,
                            margin: '24px 64px',
                            minHeight: 280,
                            
                            verticalAlign:"left"
                        }}>
                <Component/>
                </Content>
                <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
                </Layout>
            </Layout>
        </>
    );
}

export default App;