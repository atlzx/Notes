import './BackDrop.css'


const BackDrop=(props)=>{
    return (
        <div className="back-drop">
            {props.children}
        </div>
    );
            
};

export default BackDrop;