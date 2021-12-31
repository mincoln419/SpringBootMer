import React from 'react';
React.useLayoutEffect = React.useEffect;
import { Typography, Divider } from 'antd';

const { Title, Paragraph, Text, Link } = Typography;

const Index = () =>{
    return (
    <Typography>
      <Title>Introduction</Title>
      <Divider style={{ borderWidth: 2, borderColor: 'black' }} />
      <Paragraph>
        In the process of internal desktop applications development, many different design specs and
        implementations would be involved, which might cause designers and developers difficulties and
        duplication and reduce the efficiency of development.
      </Paragraph>
    </Typography>
      );
    };

export default Index;