/* alters init
 * mysql> create database onlineGap;
Query OK, 1 row affected (0.00 sec)

mysql> grant usage on *.* to root@localhost identified by 'root';
Query OK, 0 rows affected (0.03 sec)

mysql> grant all privileges on onlineGap.* to root@localhost ;
Query OK, 0 rows affected (0.03 sec)
 * 
 * */
 set global  max_allowed_packet = 1073741824;