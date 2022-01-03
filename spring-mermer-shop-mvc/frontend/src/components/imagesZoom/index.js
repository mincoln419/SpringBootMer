import React, { useState } from 'react';
import Slick from 'react-slick';
import { Global, Header, ImgWrapper, Overlay, SlickWrapper } from './styles';


const ImagesZoom = ({images, onClose}) => {

    const [currentSlide, setCurrentSlide] = useState(0);

    return (
        <Overlay>
            <Global/>
            <Header>
            <h1>상세이미지</h1>
            <button onClick={onClose}>X</button>
            </Header>
                <SlickWrapper>
                    <Slick
                        initialSlide={0}
                        beforeChange={(slide) => setCurrentSlide(slide)}
                        infinite
                        arrows={false}
                        slidesToShow={1}
                        slidesToScroll={1}
                        dots={true}
                    >
                        {images.map((v) => (
                            <ImgWrapper key={v.src}>
                                <img src={v.src} alt={v.src}/>
                            </ImgWrapper>                            
                        ))}
                    </Slick>
                </SlickWrapper>
        </Overlay>

    )

}

export default ImagesZoom;