DELETE
FROM
  vacancy;

DELETE
FROM
  company;

INSERT
INTO
  company
  (id, name, description, founded, website, is_work_from_home)
VALUES
  (10, 'Apple', 'Apple company', 1999, 'apple.com', true);

INSERT
INTO
  vacancy
  (id, company_id, title, description, is_open)
VALUES
  (1, 10, 'QA1', 'Automation QA', false),
  (2, 10, 'QA2', 'Manual QA', true);