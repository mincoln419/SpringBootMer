import React  from 'react';

import Link from 'next/link';
import { Menu, Icon } from 'antd';


export default class Sidebars extends React.Component {
	constructor () {
		super()
		
	}
    handleClick = (e) => {
		console.log(e)
		//this.props.getValue(e.key,e.item.props.children); 
	}
    render () {
				return (
					<Menu
						onClick={this.handleClick}
						//onChange = {this.getValue}
						style={{ width: 256,height:'90vh',overflow: 'auto', minWidth:256}}
						defaultSelectedKeys={['1']}
						defaultOpenKeys = {['sub10']}
						mode="inline"
					>

						<Menu key="sub1" title={<span><Icon type="smile-o" /><span>메뉴</span></span>}>
								<Menu.Item key="1">
									<Link href="/notice"><a>notice</a></Link>
								</Menu.Item>
								<Menu.Item key="2">
									<Link href="/login"><a>Login</a></Link>
								</Menu.Item>
								<Menu key="sub10" title="law">
									<Menu.Item key="3">
										<Link href="/law"><a>Law</a></Link>
									</Menu.Item>

								</Menu>
						</Menu>

					</Menu>
				);
			}
  }