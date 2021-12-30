import React from 'react';
import { Form, Input, Button, Checkbox } from 'antd';
import Index from '.';
import axios from 'axios';
const {useEffect, useState} = require('react');

const Login = () => {

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const onFinish = (data) => {
        data.grant_type = 'password';
      console.log('Success:', data);

/**
 *       HTTP Method = POST
      Request URI = /oauth/token
       Parameters = {username=[adminmermer020304191011], password=[adminmermer11012934567], grant_type=[password]}
          Headers = [Authorization:"Basic bWVyQXBwMDIwMzA0MTkxMDExMjptZXJtZXIxMTAxMjkzNDU2NzE="]
             Body = null
    Session Attrs = {}
 * 
 * */
      const token = 'Basic ' + window.btoa("" + ':' + ""); //basic auth 생성 공통 프로토콜
      console.log(token);
      axios.request({
        url: "/oauth/token",
        method: "post",
        baseURL: "/",
        headers: {'Content-Type' : 'form-data'},
        auth: {//Basic Auth를 만들어주는 해더부분임
          username: 'merApp0203041910112',
          password: 'mermer110129345671'
        },
        params : {
            username: data.username,
            password: data.password,
            grant_type: 'password'
        }
      }).then(function(res) {
        console.log(res);  
      });

    }
  
    const onFinishFailed = (errorInfo) => {
      console.log('Failed:', errorInfo);
    };
  
    return (
    <>
        {isLoggedIn ? <Index/> :
    
      <Form
        name="basic"
        labelCol={{
          span: 7,
        }}
        wrapperCol={{
          span: 10,
        }}
        initialValues={{
          remember: true,
        }}
        onFinish={onFinish}
        onFinishFailed={onFinishFailed}
        onSu
        autoComplete="off"
      >
        <Form.Item
          label="Username"
          name="username"
          autoComplete="on"
          rules={[
            {
              required: true,
              message: 'Please input your username!',
            },
          ]}
        >
          <Input />
        </Form.Item>
  
        <Form.Item
          label="Password"
          name="password"
          autoComplete="on"
          rules={[
            {
              required: true,
              message: 'Please input your password!',
            },
          ]}
        >
          <Input.Password />
        </Form.Item>
  
        <Form.Item
          name="remember"
          valuePropName="checked"
          wrapperCol={{
            offset: 7,
            span: 16,
          }}
        >
          <Checkbox>Remember me</Checkbox>
        </Form.Item>
  
        <Form.Item
          wrapperCol={{
            offset: 7,
            span: 16,
          }}
        >
          <Button type="primary" htmlType="submit">
            Submit
          </Button>
        </Form.Item>
      </Form>
        }
      </>
    );
  };
  

export default Login;