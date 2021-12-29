import React from 'react';
import { Form, Input, Button, Checkbox } from 'antd';
import Index from './Index';
import axios from 'axios';
const oauth = require('axios-oauth-client');
const {useEffect, useState} = require('react');

const Login = () => {

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const onFinish = (data) => {
        data.grant_type = 'password';
      console.log('Success:', data);

      axios.request({
        url: "/oauth/token",
        method: "post",
        baseURL: "/",
        auth: {
          username: data.username,
          password: data.password
        },
        data: {
          "grant_type": "client_credentials",
          "scope": "read write"    
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