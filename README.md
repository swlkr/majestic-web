# majestic

## Usage

```bash
lein new majestic your-project-name
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
│   │   ├── 20170410191739_add_hstore_ext.edn
│   │   └── 20170410191739_create_users_table.edn
│   └── sql
│       └── users.sql
├── src
│   └── your-project-name
│       ├── components.clj
│       ├── core.clj
│       ├── db.clj
│       ├── env.clj
│       ├── logic
│       │   ├── sessions.clj
│       │   └── users.clj
│       ├── middleware.clj
│       ├── responses.clj
│       ├── routes
│       │   ├── home.clj
│       │   ├── login.clj
│       │   ├── logout.clj
│       │   └── signup.clj
│       ├── routes.clj
│       ├── server.clj
│       └── utils.clj
└── test
    └── your-project-name
        ├── db_test.clj
        ├── logic
        │   ├── sessions_test.clj
        │   └── users_test.clj
        └── server_test.clj
```

You can do a few different things, you can run all the tests with `lein test` or you can `lein repl` in and type `(start)` or you can deploy straight to heroku and run `heroku run lein migrate` to get the db up and running. It gives you what I think is the smallest set of working code to start a majestic monolith.
