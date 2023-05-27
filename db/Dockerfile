FROM postgres:14
EXPOSE 5432
COPY restore.sh /docker-entrypoint-initdb.d/
COPY university_db_create.sql /var/lib/postgresql/backup/
