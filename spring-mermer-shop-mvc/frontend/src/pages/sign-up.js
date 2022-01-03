import React from 'react';
React.useLayoutEffect = React.useEffect;
import { Form, Input, InputNumber, Button, Divider, Typography, Space , Checkbox } from 'antd';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import {loginAction} from '../reducer/user';
import { useRouter } from 'next/router'
const { Title, Paragraph, Text } = Typography;
const layout = {
    labelCol: {
      span: 5,
    },
    wrapperCol: {
      span: 16,
    },
  };
  /* eslint-disable no-template-curly-in-string */
  
const validateMessages = {
    required: '${label} is required!',
    types: {
      email: '${label} is not a valid email!',
      number: '${label} is not a valid number!',
    },
    number: {
      range: '${label} must be between ${min} and ${max}',
    },
  };
  /* eslint-enable no-template-curly-in-string */
  

  const options = [
    { label: 'Administration', value: 'ADMIN' },
    { label: 'User', value: 'USER' }
  ];

  function onChange(checkedValues) {
    console.log('checked = ', checkedValues);
  }


const SignUp = () => {
  const router = useRouter();
  const dispatch = useDispatch();
      const onFinish = (values) => {
        const loginId = values.user.login;
        axios.request({
          url: "/api/account",
          method: "post",
          baseURL: "/",
          headers: {'Content-Type' : 'application/json;charset=UTF-8',
                    'Accept':'application/hal+json'
                },
          data: values.user
        }).then(function(res) {
          dispatch(loginAction({loginId}));
          
          router.push("/");
        });
      };
    
      return (
        <>
        <Title>회원가입</Title>
        <Divider style={{ borderWidth: 2, borderColor: 'black' }} />
        <Form {...layout} name="nest-messages" onFinish={onFinish} validateMessages={validateMessages}>
          <Form.Item
            name={['user', 'login']}
            label="Login ID"
            rules={[
              {
                required: true,
              },
            ]}
          >
             <Input />
            </Form.Item>
           <Form.Item
        name={['user', "pass"]}
        label="Password"
        rules={[
          {
            required: true,
            message: 'Please input your password!',
          },
        ]}
        hasFeedback
      >
        <Input.Password />
      </Form.Item>

      <Form.Item
        name="confirm"
        label="Confirm Password"
        dependencies={['pass']}
        hasFeedback
        rules={[
          {
            required: true,
            message: 'Please confirm your password!',
          },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if (!value || getFieldValue('pass') === value) {
                return Promise.resolve();
              }

              return Promise.reject(new Error('The two passwords that you entered do not match!'));
            },
          }),
        ]}
      >
        <Input.Password />
      </Form.Item>
      <Form.Item
            name={['user', 'username']}
            label="User Name"
            rules={[
              {
                required: true,
              },
            ]}
          >
             <Input />
            </Form.Item>
          <Form.Item
            name={['user', 'email']}
            label="Email"
            rules={[
              {
                type: 'email',
              },
            ]}
          >
            <Input />
          </Form.Item>
          <Form.Item
            name={['user', 'hpNum']}
            label="phone"
            rules={[
              {
                max:11
              },
            ]}
          >
            <Input />
          </Form.Item>
          
          <Form.Item
            name={['user', 'role']}
            label="Role"
          >
          <Checkbox.Group options={options} defaultValue={['USER']} onChange={onChange} />
          <br />
          <br /> 

          </Form.Item>

          <Form.Item wrapperCol={{ ...layout.wrapperCol, offset: 8 }}>
            <Button type="primary" htmlType="submit">
              Submit
            </Button>
          </Form.Item>
        </Form>
        </>
      );
    };

export default SignUp;