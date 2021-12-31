import React, { useState } from 'react';
import { Avatar } from 'antd';
import { UserOutlined, SettingFilled } from '@ant-design/icons';
import styled from 'styled-components';

const ProfileWrapper = styled.div`
`;

const Profile = () => {
    return (<>
    <ProfileWrapper>
    <Avatar size="large" icon={<UserOutlined />} />
    <SettingFilled />
    </ProfileWrapper>
    </>)
}

export default Profile;