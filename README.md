# megapay-example

Megapay Example
Library: `megapay-request` 


## Tree

- **source_library**: Source code của thư viện `megapay-request`.
- **demo_library**: Project demo sử dụng thư viện `megapay-request`.

## Tutorial 

- Add thư viện: `megapay-request` vào project.
- Tạo Request tới server của Megapay:

```
MgpRequest request = new MgpRequestBuilder()
                        .withUserInfo(project_id, account, username)
                        .withCardInfo(provider, code, serial)
                        .withResultListener(new MgpRequestHandler.OnResultListener() {
                            @Override
                            public void onResultListener(ResultRequest result) {
                                // xử lý dữ liệu nhận được từ server
                            }
                        })
                        .build();
                request.sendRequest();
```

- **Chú ý**: các phương thức trên là bắt buộc:
  - `withUserInfo`: chứa thông tin tài khoản người dùng.
  - `withCardInfo`: thông tin thẻ: nhà cung cấp, mã số sau lớp giấy bạc, mã serial.
  - `withResultListener`: nhận response từ server. Không xử lý dữ liệu nhận được tương tác trực tiếp với UI, cần tạo Mesage rồi vứt vào Handler để xử lý dữ liệu nhận được.
  - `sendRequest`: gửi request đi.

- Thêm permisson `INTERNET` trong `AndroidManifest`.
