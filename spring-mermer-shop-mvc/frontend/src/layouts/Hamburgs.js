import React, { useState }  from 'react';

import { Menu , Button} from 'antd';
import { AppstoreOutlined, MailOutlined, SettingOutlined, MenuOutlined, MenuFoldOutlined  } from '@ant-design/icons';
import Link from "next/link";
import styled, { createGlobalStyle } from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';
import { logoutAction } from '../reducer/user';
import { setToggleBarAction, setToggleMenuAction } from '../reducer/menu';
const { SubMenu } = Menu;
    
const NavTop = styled.div`
  position: absolute;
  justify-content: flex-end;
  button {
    background: #282828;
    border: none;
  }
  left:0 ;
    top: 0;
`;

const MenuWrapper = styled.div`
    position: absolute;
    z-index : 10;
    background-color: grey;
    left: 0;
    top: 60px;
    width: 120px;
`;

    
const Hamburgs = () =>{
    const dispatch = useDispatch();
    const {isLoggedIn} = useSelector((state) => state.user);
    const {login} = useSelector((state) => state.user.Account);

    function logout() {
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
	const {toggleMenu, toggleBar} = useSelector((state) => state.menu);
    const handleClick = e => {
		  console.log('click ', e);
		};
	  
        const toggleChange = () => {
            dispatch(setToggleMenuAction(!toggleMenu));
            dispatch(setToggleBarAction(!toggleBar));
          }
        
        const onMenuClick = () => {
            dispatch(setToggleMenuAction(!toggleMenu));
            dispatch(setToggleBarAction(!toggleBar));
        }

		return (
            <>
        
        <NavTop>
          <Button type="primary" onClick={toggleChange} style={{ marginBottom: 16, marginTop : 16, marginRight: 0}}>
            { toggleBar ? <MenuOutlined /> : <MenuFoldOutlined /> }
          </Button>
        </NavTop>
        {toggleMenu &&
        <MenuWrapper>
			<Menu
			  onClick={handleClick}
              inlineCollapsed={toggleBar}
              onClick={onMenuClick}
              theme='light'
			  mode="inline"
			>  
			  <SubMenu key="sub1" icon={<MailOutlined />} title="Navigation One">
				<Menu.ItemGroup key="g1" title="Item 1">
				  <Menu.Item key="1"><Link href="/notices"><a>공지사항</a></Link></Menu.Item>
				  <Menu.Item key="2">Option 2</Menu.Item>
				</Menu.ItemGroup>
				<Menu.ItemGroup key="g2" title="Item 2">
				  <Menu.Item key="3">Option 3</Menu.Item>
				  <Menu.Item key="4">Option 4</Menu.Item>
				</Menu.ItemGroup>
			  </SubMenu>
			  <SubMenu key="sub2" icon={<AppstoreOutlined />} title="Navigation Two">
				<Menu.Item key="5">Option 5</Menu.Item>
				<Menu.Item key="6">Option 6</Menu.Item>
				<SubMenu key="sub3" title="Submenu">
				  <Menu.Item key="7">Option 7</Menu.Item>
				  <Menu.Item key="8">Option 8</Menu.Item>
				</SubMenu>
			  </SubMenu>
			  <SubMenu key="sub4" icon={<SettingOutlined />} title="Navigation Three">
				<Menu.Item key="9">Option 9</Menu.Item>
				<Menu.Item key="10">Option 10</Menu.Item>
				<Menu.Item key="11">Option 11</Menu.Item>
				<Menu.Item key="12">Option 12</Menu.Item>
			  </SubMenu>
			</Menu>
      </MenuWrapper>
            }
            </>
		  );
	}
	  

  export default Hamburgs;