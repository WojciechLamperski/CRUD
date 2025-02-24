import json
import pymysql
import os
from dotenv import load_dotenv

# Loading .env files
load_dotenv()

DB_HOST = os.getenv('DB_TEST_HOST')
DB_USER = os.getenv('DB_TEST_USER')
DB_PASSWORD = os.getenv('DB_TEST_PASSWORD')
DB_NAME = os.getenv('DB_TEST_NAME')

# DB_HOST = os.getenv('DB_TEST_HOST')
# DB_USER = os.getenv('DB_TEST_USER')
# DB_PASSWORD = os.getenv('DB_TEST_PASSWORD')
# DB_NAME = os.getenv('DB_TEST_NAME')

# Database connection
config = pymysql.connect(host=DB_HOST, user=DB_USER, password=DB_PASSWORD, db=DB_NAME)
cursor = config.cursor()

# SQL queries
yearsSql = "INSERT INTO `years` (`year_id`, `year`) VALUES (%(year_id)s, %(year)s)"
voivodeshipsSql = "INSERT INTO `voivodeships` (`voivodeship_id`, `voivodeship`) VALUES (%(voivodeship_id)s, %(voivodeship)s)"
districtsSql = "INSERT INTO `districts` (`district_id`, `district`, `voivodeship_id`) VALUES (%(district_id)s, %(district)s, %(voivodeship_id)s)"
populationSql = "INSERT INTO `populations` (`population_id`, `year_id`, `district_id`, `women`, `men`) VALUES (%(population_id)s, %(year_id)s, %(district_id)s, %(women)s, %(men)s)"

# Read the districts JSON file
with open('data.json', 'r', encoding='utf-8') as file:
    theData = json.load(file)

# lists to be filled
years = []
voivodeships = []
districts = []
population = []

# used for iterations
seenYears = set()
seenVoivodeships = set()
seenDistricts = set()

lastPopulation = {
    "population_id": 0,
    "year_id": 0,
    "district_id": 0,
    "women": 0,
    "men": 0
}

for idx, item in enumerate(theData):

    # Append to "years" list
    tuple_check = ("year", item['Rok'])

    if tuple_check not in seenYears:
        seenYears.add(tuple_check)
        data = {
            "year_id": len(years) + 1,
            "year": item['Rok']
        }
        years.append(data)
        cursor.execute(yearsSql, data)


    # Append to "voivodeships" list
    tuple_check = ("voivodeship", item['Wojew\u00f3dztwo'])

    if tuple_check not in seenVoivodeships:
        seenVoivodeships.add(tuple_check)
        data = {
            "voivodeship_id": len(voivodeships) + 1,
            "voivodeship": item['Wojew\u00f3dztwo']
        }
        voivodeships.append(data)
        cursor.execute(voivodeshipsSql, data)


    # Append to "districts" list.
    voivodeship_id = 0

    for voivodeship in voivodeships:
        if voivodeship["voivodeship"] == item['Wojew\u00f3dztwo']:
            voivodeship_id = voivodeship["voivodeship_id"]

    tuple_check = (
        ("district", item['Powiat']),
        ("voivodeship_id", voivodeship_id)
    )

    if tuple_check not in seenDistricts:
        seenDistricts.add(tuple_check)
        data={
            "district_id": len(districts) + 1,
            "district": item['Powiat'],
            "voivodeship_id": voivodeship_id
        }
        districts.append(data)
        cursor.execute(districtsSql, data)

    # Append to "population" list.
    # Get year_id and district_id

    district_id = 0
    year_id = 0
    this_district = ""

    for district in districts:
        if district["district"] == item["Powiat"]:
            district_id = district["district_id"]

    for year in years:
        if year["year"] == item["Rok"]:
            year_id = year["year_id"]

    if lastPopulation["district_id"] == district_id and lastPopulation["year_id"] == year_id:
        lastPopulation["women"] = int(lastPopulation["women"]) + int(item['Kobiety'])
        lastPopulation["men"] = int(lastPopulation["men"]) + int(item['Mężczyźni'])
    else:
        if lastPopulation["population_id"] != 0:
            population.append(lastPopulation)
            cursor.execute(populationSql, lastPopulation)
        lastPopulation["population_id"] = lastPopulation["population_id"] + 1
        lastPopulation["year_id"] = year_id
        lastPopulation["district_id"] = district_id
        lastPopulation["women"] = item['Kobiety']
        lastPopulation["men"] = item['Mężczyźni']

    if idx+1 == len(theData):
        population.append(lastPopulation)
        cursor.execute(populationSql, lastPopulation)


config.commit()
config.close()

# print(years)
# print(voivodeships)
# print(districts)
# print(population)