
import { UploadOutlined } from '@ant-design/icons';
import { Button, message, Upload } from 'antd';

const Test = () => {
    const props = {
        name: "file",
        action: "http://localhost:8080/fileUpload",
        onChange(info) {
            if (info.file.status !== "uploading") {
                console.log(info.file, info.fileList);
            }
            if (info.file.status === "done") {
                message.success(`${info.file.name} file uploaded successfully`);
            } else if (info.file.status === "error") {
                message.error(`${info.file.name} file upload failed.`);
            }
        },
        accept:'image/*'
  };
  return (
    <Upload {...props}>
        <Button icon={<UploadOutlined />}>Click to Upload</Button>
    </Upload>
  );
};

export default Test;
