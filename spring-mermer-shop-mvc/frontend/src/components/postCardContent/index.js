import React, { useState } from 'react';
import Link from 'next/link';


const PostCardContent = ({postData}) => {

    return (
        <div>
            {postData.split(/(#[^\s,]+)/g).map((v, index) => {

                if(v.match(/(#[^\s,]+)/)){
                    return <Link href={`/hashtag/${v.slice(1)}`} key={index}><a>{v}</a></Link>
                }
                    else return v;
                })}

        </div>


    );



}

export default PostCardContent;