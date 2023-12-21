-- To get all tables
SHOW TABLES;

-- To get number of columns in a table
select *
from INFORMATION_SCHEMA.COLUMNS
where TABLE_NAME='tableName'


SQL Sample queries:
*** This query finds active clients with downloaded applications for a particular affiliate network ***/
SELECT
   c.id AS clientId,
   c.name AS clientName,
   s.id AS sourceId,
   s.name AS affiliateNetwork,
   s.url AS affiliateNetworkUrl,
   sc.username AS sourceUsername,
   sc.passwordHash AS sourcePassword,
   aa.affiliateId,
   aa.affiliateName,
   aa.affiliateCode,
   aa.regionCountry,
   aa.feedbackScore,
   aa.description,
   aa.comments,
   aa.status
FROM affiliateApplications aa
LEFT JOIN clients c ON aa.clientId = c.id
LEFT JOIN sourceClients sc ON aa.clientId = sc.clientId AND aa.sourceId = sc.sourceId
LEFT JOIN sources s ON sc.sourceId = s.id
WHERE aa.createdAt >= '2023-02-02 00:00:00' /** TODAY'S DATE **/
AND aa.sourceId = 0 /** SOURCE ID **/
AND aa.clientId IN (SELECT id FROM clients WHERE status = 'Active' AND isSaasClient = 0)
ORDER BY aa.createdAt DESC;


—-----------------------
    /*** This query finds active clients with downloaded applications for a particular affiliate network ***/
SELECT
   c.id AS clientId,
   c.name AS clientName,
   s.id AS sourceId,
   s.name AS affiliateNetwork,
   s.url AS affiliateNetworkUrl,
   sc.username AS sourceUsername,
   sc.passwordHash AS sourcePassword,
   aa.affiliateId,
   aa.affiliateName,
   aa.affiliateCode,
   aa.regionCountry,
   aa.feedbackScore,
   aa.description,
   aa.comments,
   aa.status
FROM affiliateApplications aa
LEFT JOIN clients c ON aa.clientId = c.id
LEFT JOIN sourceClients sc ON aa.clientId = sc.clientId AND aa.sourceId = sc.sourceId
LEFT JOIN sources s ON sc.sourceId = s.id
WHERE aa.createdAt >= '2023-02-02 00:00:00' /** TODAY'S DATE **/
AND aa.sourceId = 0 /** SOURCE ID **/
AND aa.clientId IN (SELECT id FROM clients WHERE status = 'Active' AND isSaasClient = 0)
ORDER BY aa.createdAt DESC;


—-------------------
SELECT COUNT(*) AS numOfApplications, aa.clientId
FROM affiliateApplications aa
WHERE aa.status = 'PENDING'
GROUP BY aa.clientId;




—-----------------------
/*** This will insert an affilaite application into the DB for your client ***/


INSERT INTO `sample`.`affiliateApplications`
    (`affiliateId`,
    `sourceId`,
    `clientId`,
    `affiliateName`,
    `affiliateCode`,
    `regionCountry`,
    `affiliateLogo`,
    `primaryWebsite`,
    `additionalWebsites`,
    `feedbackScore`,
    `description`,
    `comments`,
    `applicationDate`,
    `status`,
    `createdAt`,
    `updatedAt`,
    `processDate`,
    `errorMessage`,
    `autoProcessed`)
SELECT
   a.id,
   '1', /** SOURCE ID **/
   '3651', /** CLIENT ID **/
   a.name,
   '12345',
   'USA',
   a.logo,
   a.url,
   'www.test.com',
   FLOOR( 1 + RAND( ) * 5 ),
   a.description,
   'This is a test comment',
   NOW(),
   'PENDING',
   NOW(),
   NOW(),
   null,
   '',
   0
FROM affiliates a
WHERE a.id NOT IN (
  SELECT affiliateId
  FROM affiliateClients
  WHERE clientId = 3651) /** CLIENT ID **/
LIMIT 55;




/*** Provided by DEV team, this is the logic they use to calculate what client/affiliate pairs will get an offer extended ***/
SELECT
    d.clientId
    , d.affiliateId
    , d.sourceId
    , d.code
FROM (
    SELECT
        c.clientId
        , a.affiliateId
        , a.sourceId
        , a.code
    FROM (
        SELECT c.id AS clientId
        FROM clients c
        WHERE c.isSaasClient = 0 AND c.status = 'Active'
    ) c
    CROSS JOIN (
        SELECT sa.affiliateId, sa.sourceId, sa.code
        FROM affiliates a
        INNER JOIN sourceAffiliates sa ON sa.affiliateId = a.id
        WHERE a.extendOffer = 1
    ) a
) d
INNER JOIN sourceClients sc ON sc.clientId = d.clientId AND sc.sourceId = d.sourceId
LEFT JOIN affiliateClientStatus ac ON ac.affiliateId  = d.affiliateId AND ac.sourceId = d.sourceId AND ac.clientId = d.clientId
LEFT JOIN affiliateOfferExtensions aoe ON aoe.clientId = d.clientId AND aoe.sourceId = d.sourceId AND aoe.affiliateid = d.affiliateId
WHERE sc.enabled = 1
AND ac.id IS NULL
AND aoe.id IS NULL;




/*** Just to check job status when you trigger it via postman ***/
SELECT * FROM jobExecutions WHERE id = 95938;




/*** Check affiliate/client pair status. Useful to check if there is no relationship OR after the affiliate status process it should add the OFFER_EXTENDED status ***/
SELECT *
FROM affiliateClientStatus acs
WHERE clientId = 4596
AND affiliateId IN (SELECT affiliateId FROM affiliateMappings am WHERE parentId = 2417 OR affiliateId = 2417);


SELECT *
FROM affiliateClientStatus acs WHERE clientId = 5032 AND affiliateId IN (2461, 124273, 805146)


/*** Find out when the jobs executed on the environment ***/
SELECT *
FROM jobExecutions je
WHERE jobType IN ('AFFILIATE_CLIENT_STATUS_WORKER', 'AFFILIATE_OFFER_EXTENSIONS_WORKER')
ORDER BY createdAt DESC;




/*** General queries that help find information on an affiliate or client for this feature ***/
SELECT *
FROM affiliates
WHERE id IN (
    SELECT affiliateId
    FROM affiliateMappings am
    WHERE parentId = 2417
    OR affiliateId = 2417
);


SELECT * FROM affiliateApplications WHERE clientId = 541 AND affiliateId = 2461;


SELECT * FROM affiliateOfferExtensions aoe WHERE createdAt >= '2023-07-06 18:00:00';


SELECT * FROM affiliateOfferExtensions WHERE id = 101;


SELECT * FROM affiliates WHERE extendOffer = 1;


SELECT * FROM affiliates WHERE name LIKE '%AdGoal%'; -- 124273, 2461, 2417, 10410, 805146


SELECT * FROM affiliates WHERE id IN (124273, 2461, 2417, 10410, 805146);


SELECT * FROM sourceAffiliates sa WHERE affiliateId IN (10410, 2461, 2417);


SELECT * FROM sourceAffiliates WHERE code = '41227';


SELECT * FROM affiliateMappings am WHERE parentId = 574 OR affiliateId = 574;


SELECT * FROM clients WHERE id IN (5001, 5032);













