/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { PozService } from 'app/entities/poz/poz.service';
import { IPoz, Poz } from 'app/shared/model/poz.model';

describe('Service Tests', () => {
    describe('Poz Service', () => {
        let injector: TestBed;
        let service: PozService;
        let httpMock: HttpTestingController;
        let elemDefault: IPoz;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PozService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Poz(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 0, 0, false);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Poz', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Poz(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Poz', async () => {
                const returnedFromService = Object.assign(
                    {
                        ad: 'BBBBBB',
                        aciklama: 'BBBBBB',
                        kisaltma: 'BBBBBB',
                        ihaleSahasi: 'BBBBBB',
                        yil: 1,
                        tenzilatsizFiyat: 1,
                        tenzilatliFiyat: 1,
                        taseronFiyat: 1,
                        tasereFiyat: 1,
                        malzemeMi: true
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Poz', async () => {
                const returnedFromService = Object.assign(
                    {
                        ad: 'BBBBBB',
                        aciklama: 'BBBBBB',
                        kisaltma: 'BBBBBB',
                        ihaleSahasi: 'BBBBBB',
                        yil: 1,
                        tenzilatsizFiyat: 1,
                        tenzilatliFiyat: 1,
                        taseronFiyat: 1,
                        tasereFiyat: 1,
                        malzemeMi: true
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a Poz', async () => {
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
