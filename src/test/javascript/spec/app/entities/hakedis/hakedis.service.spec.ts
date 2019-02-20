/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { HakedisService } from 'app/entities/hakedis/hakedis.service';
import { IHakedis, Hakedis, IsDurumu, OdemeDurumu } from 'app/shared/model/hakedis.model';

describe('Service Tests', () => {
    describe('Hakedis Service', () => {
        let injector: TestBed;
        let service: HakedisService;
        let httpMock: HttpTestingController;
        let elemDefault: IHakedis;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(HakedisService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Hakedis(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                IsDurumu.BEKLIYOR,
                0,
                OdemeDurumu.BEKLIYOR,
                'AAAAAAA',
                'image/png',
                'AAAAAAA',
                currentDate,
                'image/png',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        tarih: currentDate.format(DATE_FORMAT)
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

            it('should create a Hakedis', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        tarih: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        tarih: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Hakedis(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Hakedis', async () => {
                const returnedFromService = Object.assign(
                    {
                        ad: 'BBBBBB',
                        aciklama: 'BBBBBB',
                        detay: 'BBBBBB',
                        durumu: 'BBBBBB',
                        seriNo: 1,
                        odemeDurumu: 'BBBBBB',
                        odemeNo: 'BBBBBB',
                        resim: 'BBBBBB',
                        tarih: currentDate.format(DATE_FORMAT),
                        dosya: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        tarih: currentDate
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

            it('should return a list of Hakedis', async () => {
                const returnedFromService = Object.assign(
                    {
                        ad: 'BBBBBB',
                        aciklama: 'BBBBBB',
                        detay: 'BBBBBB',
                        durumu: 'BBBBBB',
                        seriNo: 1,
                        odemeDurumu: 'BBBBBB',
                        odemeNo: 'BBBBBB',
                        resim: 'BBBBBB',
                        tarih: currentDate.format(DATE_FORMAT),
                        dosya: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        tarih: currentDate
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

            it('should delete a Hakedis', async () => {
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
