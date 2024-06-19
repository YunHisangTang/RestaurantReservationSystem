# RestaurantReservationSystem
# 功能
這是OOPL的自選題，實作了以下多項功能:

## 顧客前台功能
1. 預約功能:
  -	顯示尚可預約時段的日期和時間。
  -	記錄大人和小孩的人數。
  - 允許取消預約。
  -	是否必須在同一桌（目前規劃都在同一桌）。

2. 會員資訊：
  -	包括手機號碼、Email、預約記錄等。

3. 餐廳地點：
  - 顯示餐廳地圖，可以選擇餐廳。

## 老闆後台功能

1. 查看並編輯訂位狀況:
  - 老闆會編輯，某會員在某座位訂位、已用餐結束、取消等狀態。

2. 調整各項系統資訊：
  - 餐廳的時段設定，可自動考慮用餐時間，切割出訂位的時間段。
  - 用餐時間上限。
  - 座位數設定。
  - 搭配地圖設定。


# 資料夾目錄
- RestaurantReservationSystemBackend : 後端資料夾 (使用標準的Spring MVC寫法: Controller、Service、Repository / Dto、Model)
- RestaurantReservationSystemFrontend :　前端資料夾 (View在這裡)
- README.md
- docker-compose.yaml :　docker設定mysql
- mysql.cnf : mysql設定檔

# 套件
- 後端：
  -  Java 17： 主要的開發程式語言。
  -  Spring Boot 3：主要開發框架。
  -  Maven：用於管理及配置依賴。
  -  JWT/Spring Security：用於生成token，實現身份驗證機制。
  -  Spring Data JPA(底層仍是用JDBC實現)與部分SQL指令: 進行DB的CRUD。
  
- 前端： (可在JavaFX的Webview上啟動)
  - HTML 和 JavaScript：用於開發網頁操作介面。
  - CSS：用於網頁版面設計。

- 維運:
  - Docker : 使用docker compose 進行MySQL資料庫的設定與管理。
  - MySQL：關聯式資料庫。

# 執行
- Docker Desktop:
  - 請用 docker-compose.yaml 和 mysql.cnf 啟動 mysql。
  - docker compose up -d
  
- IntellJ:
  - 分別開啟前端和後端專案:
    - RestaurantReservationSystemBackend : 找到 RestaurantReservationSystemBackendApplication.java RUN 起來。
    - RestaurantReservationSystemFrontend : 找到 JavaFXApp.java RUN 起來。
