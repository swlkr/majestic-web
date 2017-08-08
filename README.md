# majestic web

## Usage

```bash
lein new majestic-web your-project-name
```

## What Just Happened?

Files were created! Files!

```bash
your-project-name
├── Procfile
├── README.md
├── profiles.clj
├── project.clj
├── resources
│   ├── migrations
│   │   ├── 20170805214656_add_hstore_ext.edn
│   │   └── 20170805214656_create_users_table.edn
│   └── sql
│       └── users.sql
├── src
│   └── your_project_name
│       ├── core.clj
│       ├── db.clj
│       ├── home
│       │   ├── html.clj
│       │   └── http.clj
│       ├── html.clj
│       ├── http.clj
│       ├── middleware.clj
│       ├── migrations.clj
│       ├── responses.clj
│       ├── server.clj
│       ├── sessions
│       │   ├── html.clj
│       │   ├── http.clj
│       │   └── logic.clj
│       ├── users
│       │   ├── db.clj
│       │   ├── html.clj
│       │   ├── http.clj
│       │   └── logic.clj
│       └── utils.clj
└── test
    └── your_project_name
        ├── db_test.clj
        ├── server_test.clj
        ├── sessions
        │   └── logic_test.clj
        └── users
            └── logic_test.clj
```

You can do a few different things, you can run all the tests with `lein test` or you can `lein repl` in and type `(start)` or you can deploy straight to heroku and run `heroku run lein migrate` to get the db up and running. It gives you what I think is the smallest set of working code to start a majestic monolith.
