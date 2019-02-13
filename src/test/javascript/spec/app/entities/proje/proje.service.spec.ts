/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProjeService } from 'app/entities/proje/proje.service';
import { IProje, Proje, GorevDurumu, OnemDurumu } from 'app/shared/model/proje.model';

describe('Service Tests', () => {
    describe('Proje Service', () => {
        let injector: TestBed;
        let service: ProjeService;
        let httpMock: HttpTestingController;
        let elemDefault: IProje;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProjeService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Proje(
                0,
                'AAAAAAA',
                'AAAAAAA',
                GorevDurumu.BEKLIYOR,
                OnemDurumu.ACIL,
                currentDate,
                currentDate,
                currentDate,
                'image/png',
                'AAAAAAA',
                false
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        tarih: currentDate.format(DATE_TIME_FORMAT),
                        baslamaTarihi: currentDate.format(DATE_TIME_FORMAT),
                        bitisTarihi: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a Proje', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        tarih: currentDate.format(DATE_TIME_FORMAT),
                        baslamaTarihi: currentDate.format(DATE_TIME_FORMAT),
                        bitisTarihi: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        tarih: currentDate,
                        baslamaTarihi: currentDate,
                        bitisTarihi: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Proje(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Proje', async () => {
                const returnedFromService = Object.assign(
                    {
                        isTanimi: 'BBBBBB',
                        aciklama: 'BBBBBB',
                        durumu: 'BBBBBB',
                        onemDurumu: 'BBBBBB',
                        tarih: currentDate.format(DATE_TIME_FORMAT),
                        baslamaTarihi: currentDate.format(DATE_TIME_FORMAT),
                        bitisTarihi: currentDate.format(DATE_TIME_FORMAT),
                        detay: 'BBBBBB',
                        aktifMi: true
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        tarih: currentDate,
                        baslamaTarihi: currentDate,
                        bitisTarihi: currentDate
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

            it('should return a list of Proje', async () => {
                const returnedFromService = Object.assign(
                    {
                        isTanimi: 'BBBBBB',
                        aciklama: 'BBBBBB',
                        durumu: 'BBBBBB',
                        onemDurumu: 'BBBBBB',
                        tarih: currentDate.format(DATE_TIME_FORMAT),
                        baslamaTarihi: currentDate.format(DATE_TIME_FORMAT),
                        bitisTarihi: currentDate.format(DATE_TIME_FORMAT),
                        detay: 'BBBBBB',
                        aktifMi: true
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        tarih: currentDate,
                        baslamaTarihi: currentDate,
                        bitisTarihi: currentDate
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

            it('should delete a Proje', async () => {
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
