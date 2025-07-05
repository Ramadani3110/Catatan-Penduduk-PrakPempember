### Backend API Catatan Penduduk
## How to Running this Backend
### 1. Clone this repository
```bash
git clone https://github.com/Ramadani3110/Catatan-Penduduk-PrakPempember.git
```
### 2. Enter this directory
```bash
cd catatan-penduduk-api
```
### 3. Install depedency
```bash
composer install 
or
composer update
```
### 4. Copy Environment File
```bash
cp .env.example .env
```
### 5. Setup Database Configuration
```.env
# change this line , using your database credential
DB_CONNECTION=sqlite
# DB_HOST=127.0.0.1
# DB_PORT=3306
# DB_DATABASE=laravel
# DB_USERNAME=root
# DB_PASSWORD=

# For example
DB_CONNECTION=mysql
DB_HOST=127.0.0.1
DB_PORT=3306
DB_DATABASE=catatanpenduduk
DB_USERNAME=admin
DB_PASSWORD=admin
```
### 6. Generate APP Key
```bash
php artisan key:generate
```
### 7. Migrate all table with seeders
```bash
php artisan migrate --seed
```
### 8. Running app
```bash
# default
php artisan serve
# use this if you want this app running on local network
php artisan serve --host=192.x.x.x --port=8000
```