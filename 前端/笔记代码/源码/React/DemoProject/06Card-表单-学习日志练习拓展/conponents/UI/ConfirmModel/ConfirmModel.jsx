import Card from '../Card/Card'
import './ConfirmModel.css'
import BackDrop from '../BackDrop/BackDrop';
import ReactDOM  from 'react-dom';


// 得到想要挂载的根元素DOM对象
const backdropRoot=document.getElementById("backdrop-root");

const ConfirmModel=(props)=>{

    // 调用ReactDOM.createPortal方法,方法接收两个参数:
    //    要传送的React元素对象
    //    要挂载的DOM对象,即想传送到的地方
    // 这个代码写在BackDrop.jsx也会同样生效，因为BackDrop组件在传送时会携带其子组件一起传送
    return ReactDOM.createPortal(
        (
            <BackDrop>
                <Card className="confirm-model">
                    <div className='confirm-text'>
                        <p>{props.textTip}</p>
                    </div>
                    <div className="confirm-button">
                        <button onClick={props.confirmDelete}>确认</button>
                        <button onClick={props.hideTip}>取消</button>
                    </div>
                </Card>
            </BackDrop>
        ),
        backdropRoot
    );
    
    
};

export default ConfirmModel;