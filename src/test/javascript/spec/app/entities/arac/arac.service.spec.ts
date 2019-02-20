/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AracService } from 'app/entities/arac/arac.service';
import { IArac, Arac, YakitTuru } from 'app/shared/model/arac.model';

describe('Service Tests', () => {
    describe('Arac Service', () => {
        let injector: TestBed;
        let service: AracService;
        let httpMock: HttpTestingController;
        let elemDefault: IArac;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(AracService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Arac(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                YakitTuru.DIZEL,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                currentDate,
                'image/png',
                'AAAAAAA',
                'image/png',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        tarih: currentDate.format(DATE_FORMAT),
                        muayeneTarih: currentDate.format(DATE_FORMAT),
                        kaskoTarih: currentDate.format(DATE_FORMAT),
                        sigortaTarih: currentDate.format(DATE_FORMAT),
                        bakimTarih: currentDate.format(DATE_FORMAT)
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

            it('should create a Arac', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        tarih: currentDate.format(DATE_FORMAT),
                        muayeneTarih: currentDate.format(DATE_FORMAT),
                        kaskoTarih: currentDate.format(DATE_FORMAT),
                        sigortaTarih: currentDate.format(DATE_FORMAT),
                        bakimTarih: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        tarih: currentDate,
                        muayeneTarih: currentDate,
                        kaskoTarih: currentDate,
                        sigortaTarih: currentDate,
                        bakimTarih: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Arac(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Arac', async () => {
                const returnedFromService = Object.assign(
                    {
                        ad: 'BBBBBB',
                        aciklama: 'BBBBBB',
                        detay: 'BBBBBB',
                        modelYili: 1,
                        yakitTuru: 'BBBBBB',
                        tarih: currentDate.format(DATE_FORMAT),
                        muayeneTarih: currentDate.format(DATE_FORMAT),
                        kaskoTarih: currentDate.format(DATE_FORMAT),
                        sigortaTarih: currentDate.format(DATE_FORMAT),
                        bakimTarih: currentDate.format(DATE_FORMAT),
                        resim: 'BBBBBB',
                        dosya: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        tarih: currentDate,
                        muayeneTarih: currentDate,
                        kaskoTarih: currentDate,
                        sigortaTarih: currentDate,
                        bakimTarih: currentDate
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

            it('should return a list of Arac', async () => {
                const returnedFromService = Object.assign(
                    {
                        ad: 'BBBBBB',
                        aciklama: 'BBBBBB',
                        detay: 'BBBBBB',
                        modelYili: 1,
                        yakitTuru: 'BBBBBB',
                        tarih: currentDate.format(DATE_FORMAT),
                        muayeneTarih: currentDate.format(DATE_FORMAT),
                        kaskoTarih: currentDate.format(DATE_FORMAT),
                        sigortaTarih: currentDate.format(DATE_FORMAT),
                        bakimTarih: currentDate.format(DATE_FORMAT),
                        resim: 'BBBBBB',
                        dosya: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        tarih: currentDate,
                        muayeneTarih: currentDate,
                        kaskoTarih: currentDate,
                        sigortaTarih: currentDate,
                        bakimTarih: currentDate
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

            it('should delete a Arac', async () => {
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
