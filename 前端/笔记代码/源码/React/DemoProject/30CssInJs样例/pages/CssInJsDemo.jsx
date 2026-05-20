import styled, { createGlobalStyle } from "styled-components";
// 一般情况下，需要以 styled.[标签名]`[CSS语法]` 的格式编写CSS
// 如果是React，那么这东西会返回一个组件，组件内默认包含一个styled.后面跟着的标签
// ``里面的CSS支持类似SCSS的嵌套语法，因此最外层的CSS格式就是styled.后面跟着的标签的CSS样式
// 而嵌套的CSS格式就可以使用CSS原生的选择器进行相关操作了
const ButtonContainer = styled.div`
    height: 100px;
    background-color: ${(props) => {
        return props.color ? props.color : "red";
    }};
    display: flex;
    justify-content: center;
    align-items: center;
    .button {
        border-radius: 10px;
        height: 80px;
        width: 300px;
    }
`;


// 可以通过styled继续拓展原有组件
const ContainerPlus = styled(ButtonContainer)`
    width: 200px;
    .button{
        color: skyblue;
    }
`;

// 创建全局样式
const GlobalStyle = createGlobalStyle`
    body{
        margin: 0;
        padding: 0;
        font-family: Arial, sans-serif;
        background-color: #aaa;
    }
`;

// 可以通过attrs向组件传递默认属性（如表单输入框）
// 在attrs后加上``以继续设置样式
const Input = styled.input.attrs(
    (props) => {
        return {
            type: 'text',
            placeholder: props.placeholder || 'Enter text...',
        }
    }
)`
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
`;

export const CssInJsDemo = () => {
    return (
        <>
            {/* 
                用styled.[标签名]`[CSS语法]`返回的组件包住想作用的组件以使其生效 
                这里ButtonContainer组件最后会变成一个div标签包住button标签，因为在上面的styled.后面写的是div
                指定button的class就可以使上面的.button类选择器生效了
            */}
            <ButtonContainer><button className="button">未拓展组件的按钮</button></ButtonContainer>
            <ContainerPlus color={"blue"}>
                <button className="button">拓展组件的按钮</button>
            </ContainerPlus>
            {/* 全局样式自己待着，不要包住其它组件，被包住的组件无法被渲染 */}
            <GlobalStyle />
            <Input placeholder="输入些什么吧"/>
        </>
    );
};
