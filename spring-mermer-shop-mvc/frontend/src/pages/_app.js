import React, { useEffect } from 'react';
React.useLayoutEffect = React.useEffect;
import PropTypes from 'prop-types';
import Link from 'next/link';
import 'antd/dist/antd.css';
import Sidebars from "../layouts/Sidebars";
import { Layout, Row, Col, Avatar, Input, Menu, Dropdown, Icon, Badge, Button } from 'antd';
import { DownOutlined , UserOutlined} from '@ant-design/icons';
const {Header, Content, Sider, Footer} = Layout;
import { useDispatch, useSelector } from 'react-redux';
import { logInStateUpdateRequest, logoutAction, setCookieRequest } from '../reducer/user';
import wrapper from '../store/configureStore';
import { BrowserView, MobileView } from 'react-device-detect';
import Hamburgs from '../layouts/Hamburgs';
import { createGlobalStyle } from 'styled-components';
import cookies, { useCookies } from 'react-cookie';
import {END} from 'redux-saga';
import axios from 'axios';

axios.defaults.withCredentials = true;
axios.defaults.baseURL=process.env.NEXT_PUBLIC_AXIOS_BASE_URL;
axios.defaults.timeout=10000


const Global = createGlobalStyle`
    .ant-layout-header {
        padding-right: 0;
    }

`;

const App = ({ Component }) => {
    const dispatch = useDispatch();
    const {isLoggedIn, accountId } = useSelector((state) => state.user);
    const {login, token} = useSelector((state) => state.user.Account);
    const loginInfo = ['token' ,"loginId", "accountId"];
    const [cookies, setCookie, rmCookie] = useCookies(loginInfo);
    
    function logout() {//로그아웃 버튼 클릭시 쿠키 삭제
        loginInfo.map((v) => rmCookie(v));
        dispatch(logoutAction());
    }
   
    const menu = (
        <>
        <Menu>
            <Menu.Item key={"setting"}>
                <Link href="/login"><a>환경설정</a></Link>
            </Menu.Item>
            <Menu.Item key={"logout"}>
                <Button onClick={logout}>로그아웃</Button>
            </Menu.Item>
        </Menu>
        </>
    );


    return (
        <>
            <title>형량계산기</title>
            <BrowserView>
            <Layout>
                    <Header style={{ color: '#fff', textAlign: 'center', fontWeight: 'bold' }}>
                        <Row>
                            <Col span={3}>
                                <Link href="/"><a>형량계산기</a></Link>
                            </Col>
                            <Col span={5}></Col>
                            <Col span={8}><Input placeholder="검색" /></Col>
                            <Col span={8}>
                                {isLoggedIn ? (<>
                                <Avatar style={{ backgroundColor: '#87d068', marginRight: 20 }} icon={<UserOutlined/>} />
                                <Dropdown overlay={menu}>
                                 <a className="ant-dropdown-link" onClick={e => e.preventDefault()}>
                                    {login} <DownOutlined />
                                </a>
                              </Dropdown>
                              </>) : (<>
                                <ul style={{ color: '#fff', textAlign: 'center', fontWeight: 'bold' , float: "right", paddingRight : "10px"}}> 
                                    <Link  href="/login"><a style={{paddingRight : "20px"}}>Login</a></Link><span > </span>
                                    <Link href="/sign-up"><a>Sign-Up</a></Link>
                                </ul>
                              </>)}
                            </Col>
                        </Row>
                    </Header>
                    <Layout>
                        <Sider style={{ color: '#666' }}>
                            <Sidebars />
                        </Sider>
                        <Content style={{marginLeft: '5%', height: '86vh', paddingRight: '2%', overflow: 'auto', textAlign: 'center' }}>
                            <Component />
                        </Content>
                    </Layout>
                    <Footer style={{ backgroundColor: '#001529', position: 'fixed', bottom: 0, width: '100vw', minWidth: '1000px', textAlign: 'center', fontWeight: 'bold', color: '#fff' }}>
                    <a href="http://www.mermer.kr" target={'_blank'}>©2021-2022 Made by Mermer</a>
                    </Footer>
            </Layout>
            </BrowserView>
            <MobileView>
            <Hamburgs />
            <Layout>
                    <Header style={{ color: '#fff', textAlign: 'center', fontWeight: 'bold'}}>
                    <Row>
                            <Col span={4}>
                                </Col>
                            <Col span={6}>
                                <Link href="/"><a>형량계산기</a></Link>
                            </Col>
                            <Col span={12}>
                            {isLoggedIn ? (<>
                   <Avatar style={{ backgroundColor: '#87d068', marginRight: 20 }} icon={<UserOutlined/>} />
                   <Dropdown overlay={menu}>
                    <a className="ant-dropdown-link" onClick={e => e.preventDefault()}>
                       {login} <DownOutlined />
                   </a>
                 </Dropdown>
                 </>) : (<>
                   <ul style={{ color: '#fff', textAlign: 'center', fontWeight: 'bold' , float: "right", paddingRight : "10px"}}> 
                        <Link  href="/login"><a style={{paddingRight : "10px"}}>Login</a></Link>
                        <Link href="/sign-up"><a>SignUp</a></Link>
                   </ul>
                 </>)}

                            </Col>
                        </Row>
                    </Header>
                    
                    <Layout>
                        <Content style={{marginLeft: '5%', height: '86vh', paddingRight: '2%', overflow: 'auto', textAlign: 'center' }}>
                            <Component/>
                        </Content>
                    </Layout>
                    <Footer style={{ backgroundColor: '#001529', position: 'fixed', bottom: 0, width: '100vw', minWidth: '100px', textAlign: 'center', fontWeight: 'bold', color: '#fff' }}>
                    <a href="http://www.mermer.kr" target={'_blank'}>©2021-2022 Made by Mermer</a>
                    </Footer>
            </Layout>
           </MobileView>
        </>
    );
}

App.propTypes = {
    Component: PropTypes.elementType.isRequired,
  };




export default wrapper.withRedux(App);