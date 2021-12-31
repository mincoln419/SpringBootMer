import React, { useState } from 'react';
React.useEffectLayout = React.useEffect;
import axios from 'axios';
import Link from 'next/link';
import 'antd/dist/antd.css';
import Headers from "../layouts/Headers";
import Sidebars from "../layouts/Sidebars";
import { Layout, Row, Col, Avatar, Input, Menu, Dropdown, Icon, Badge } from 'antd';
import { DownOutlined } from '@ant-design/icons';
const {Header, Content, Sider, Footer} = Layout;




const App = ({ Component }) => {

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [token, setToken] = useState([""]);

    const menu = (
        <>
        <Menu>
            <Menu.Item>
                <Link href="/login"><a>환경설정</a></Link>
            </Menu.Item>
            <Menu.Item>
                <Link href="/login"><a>로그아웃</a></Link>
            </Menu.Item>
        </Menu>
        </>
    );

    const getValue = (value) => {
        //const [value] = useState([value]);
    }

    return (
        <>
            <title>형량계산기</title>
            <Layout>
                    <Header style={{ color: '#fff', textAlign: 'center', fontWeight: 'bold' }}>
                        <Row>
                            <Col span={10}>
                                형량계산기
                            </Col>
                            <Col span={8}><Input placeholder="검색" /></Col>
                            <Col span={6}>
                                {isLoggedIn ? (<>
                                <Avatar style={{ backgroundColor: '#87d068', marginRight: 20 }} icon="user" />
                                <Dropdown overlay={menu}>
                                 <a className="ant-dropdown-link" onClick={e => e.preventDefault()}>
                                    Mermer <DownOutlined />
                                </a>
                              </Dropdown>
                              </>) : (<>
                                <ui style={{ color: '#fff', textAlign: 'center', fontWeight: 'bold' , float: "right"}}> 
                                    <Link href="/login"><a>Login</a></Link><span > </span>
                                    <Link href="/sign-up"><a>Sign-Up</a></Link>
                                </ui>
                              </>)}
                            </Col>
                        </Row>
                    </Header>
                    <Layout>
                        <Sider style={{ color: '#666' }}>
                            <Sidebars />
                        </Sider>
                        <Content style={{ marginLeft: '5%', height: '86vh', paddingRight: '2%', overflow: 'auto', textAlign: 'center' }}>
                            <Component />
                        </Content>
                    </Layout>
                    <Footer style={{ backgroundColor: '#001529', position: 'fixed', bottom: 0, width: '100vw', minWidth: '1000px', textAlign: 'center', fontWeight: 'bold', color: '#fff' }}>
                    <a href="http://www.mermer.kr" target={'_blank'}>©2021 Created by Mermer</a>
                    </Footer>
                </Layout>
        </>
    );
}

export default App;