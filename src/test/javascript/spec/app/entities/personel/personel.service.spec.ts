/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PersonelService } from 'app/entities/personel/personel.service';
import { IPersonel, Personel, Cinsiyet, EgitimDurumu, KanGrubu, PersonelTuru, MedeniHali } from 'app/shared/model/personel.model';

describe('Service Tests', () => {
    describe('Personel Service', () => {
        let injector: TestBed;
        let service: PersonelService;
        let httpMock: HttpTestingController;
        let elemDefault: IPersonel;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PersonelService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Personel(
                0,
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                Cinsiyet.ERKEK,
                EgitimDurumu.ILKOKUL,
                KanGrubu.A_RH_POZITIF,
                PersonelTuru.MAASLI,
                0,
                'AAAAAAA',
                MedeniHali.BEKAR,
                currentDate,
                currentDate,
                currentDate,
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dogumTarihi: currentDate.format(DATE_FORMAT),
                        girisTarihi: currentDate.format(DATE_FORMAT),
                        cikisTarihi: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Personel', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dogumTarihi: currentDate.format(DATE_FORMAT),
                        girisTarihi: currentDate.format(DATE_FORMAT),
                        cikisTarihi: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dogumTarihi: currentDate,
                        girisTarihi: currentDate,
                        cikisTarihi: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Personel(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Personel', async () => {
                const returnedFromService = Object.assign(
                    {
                        tckimlikno: 1,
                        ad: 'BBBBBB',
                        cepTelefon: 'BBBBBB',
                        sabitTelefon: 'BBBBBB',
                        eposta: 'BBBBBB',
                        cinsiyet: 'BBBBBB',
                        egitimDurumu: 'BBBBBB',
                        kanGrubu: 'BBBBBB',
                        personelTuru: 'BBBBBB',
                        ucret: 1,
                        iban: 'BBBBBB',
                        medeniHali: 'BBBBBB',
                        dogumTarihi: currentDate.format(DATE_FORMAT),
                        girisTarihi: currentDate.format(DATE_FORMAT),
                        cikisTarihi: currentDate.format(DATE_FORMAT),
                        resim: 'BBBBBB',
                        dosya: 'BBBBBB',
                        not: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dogumTarihi: currentDate,
                        girisTarihi: currentDate,
                        cikisTarihi: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Personel', async () => {
                const returnedFromService = Object.assign(
                    {
                        tckimlikno: 1,
                        ad: 'BBBBBB',
                        cepTelefon: 'BBBBBB',
                        sabitTelefon: 'BBBBBB',
                        eposta: 'BBBBBB',
                        cinsiyet: 'BBBBBB',
                        egitimDurumu: 'BBBBBB',
                        kanGrubu: 'BBBBBB',
                        personelTuru: 'BBBBBB',
                        ucret: 1,
                        iban: 'BBBBBB',
                        medeniHali: 'BBBBBB',
                        dogumTarihi: currentDate.format(DATE_FORMAT),
                        girisTarihi: currentDate.format(DATE_FORMAT),
                        cikisTarihi: currentDate.format(DATE_FORMAT),
                        resim: 'BBBBBB',
                        dosya: 'BBBBBB',
                        not: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dogumTarihi: currentDate,
                        girisTarihi: currentDate,
                        cikisTarihi: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Personel', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
