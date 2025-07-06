# Setup
### 1. Open build.gradle.kts (module app) change using your backend server api
```
buildConfigField("String","BASE_URL","\"http://192.x.x.x:8000/\"")
```
### 2. Change network security configuration in res/xml/network_security_config.xml
```xml
<!- change this line to your local ip -!>
<domain includeSubdomains="false">192.168.100.154</domain>
```
### 3. Compile All Resources
### 4. Run